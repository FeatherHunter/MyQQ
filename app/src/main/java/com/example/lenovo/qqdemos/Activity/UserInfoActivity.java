package com.example.lenovo.qqdemos.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.Activity.Friend.AddContactActivity;
import com.example.lenovo.qqdemos.Beans.UserInfo;
import com.example.lenovo.qqdemos.CustomView.TopBar;
import com.example.lenovo.qqdemos.GGIMHelper;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Util.MeasureUtil;
import com.example.lenovo.qqdemos.Util.PathUtil;
import com.example.lenovo.qqdemos.Util.PictureBrowse.activity.AlbumsActivity;
import com.example.lenovo.qqdemos.Util.ResUtil;
import com.example.lenovo.qqdemos.Util.ToastUtil;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.pkmmte.view.CircularImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/*-----------------------------------------
 *            显示详细的用户信息
 * ---------------------------------------*/
public class UserInfoActivity extends Activity {

    CircularImageView headImage;
    PopupWindow popupWindow;

    View bottomView;

    String myID = EMClient.getInstance().getCurrentUser();//当前用户的ID

    BmobFile bmobFile;
    UserInfo userInfo;

    //
    TopBar topBar;

    //头像选取、裁剪 activity的请求码
    static final int HEAD_IMAGE_UPLOAD_REQUEST = 0;
    static final int HEAD_IMAGE_CLIP_REQUEST = 1;

    String TAG = getClass().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        headImage= (CircularImageView) findViewById(R.id.user_info_head_image);
        /*--------------------------------
         *  标题栏各项配置
         * -------------------------*/
        topBar = (TopBar) findViewById(R.id.user_info_topbar);
        topBar.setTitleTextVisibility(View.VISIBLE);
        topBar.setLeftTextVisibility(View.VISIBLE);
        topBar.setRightTextVisibility(View.VISIBLE);
        topBar.setLeftTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.this.finish();
            }
        });
        iniPopupWindow();
        /*----------------------------------------------
        *  GGIMHelper:自定义帮助类
        *    用于加载头像
        * ----------------------------------------*/
        GGIMHelper.getInstance().loadHeadImage(myID, headImage);

        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (popupWindow.isShowing()) {  //检测PopupWindow的状态
                        popupWindow.dismiss();   //关闭PopupWindow
                    } else {
//                        popupWindow.showAsDropDown(bottomView, 0, popupWindow.getContentView().getMeasuredHeight()/2);
//                        ToastUtil.toast(UserInfoActivity.this, " "+popupWindow.getContentView().getMeasuredHeight());
                        //显示在底层
                        popupWindow.showAtLocation(UserInfoActivity.this.findViewById(R.id.user_info_main_layout),
                                Gravity.NO_GRAVITY,
                                0,  //popwindow  x offset
                                MeasureUtil.getRealScreenSize(UserInfoActivity.this).y-MeasureUtil.getVirtualBarHeigh(UserInfoActivity.this));

                    }

            }
        });
    }

    private void iniPopupWindow() {
        //      View contentView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.list_item_popupwindow, null);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.user_info_popupwindow, null);  //加载一个布局文件

        TextView takePhoto = (TextView) contentView.findViewById(R.id.user_info_take_photo);
        TextView albumb = (TextView) contentView.findViewById(R.id.user_info_album);
        TextView cancel = (TextView) contentView.findViewById(R.id.user_info_cancel);

        popupWindow = new PopupWindow(contentView);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*-------------------------------------------
         *        利用系统相册进行头像设置
         *-----------------------------------------*/
        albumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(UserInfoActivity.this, AlbumsActivity.class));
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,HEAD_IMAGE_UPLOAD_REQUEST);
            }
        });
        //"取消"
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);   //设置宽度
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT); //设置高度

        //强行绘制，得到高度，宽度
        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        //设置PopWindow点击屏幕其他地方消失
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //       popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tank2));//设置背景图片，不能在布局中设置，要在代码中设置
        popupWindow.setOutsideTouchable(true);  //触摸PopupWindow外部，popupWindow消失

    }

    /**---------------------------------------------
     *  照片选择和照片裁剪 Activity返回时进行处理
     *
     * @param requestCode 请求码
     *-----------------------------------------------*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case HEAD_IMAGE_UPLOAD_REQUEST:

                if (popupWindow.isShowing()) {  //检测PopupWindow的状态
                    popupWindow.dismiss();   //关闭PopupWindow
                }

                Uri uri = null;
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(this, "SD不可用",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    uri = data.getData();
                    startImageAction(uri, 200, 200, HEAD_IMAGE_CLIP_REQUEST, true);
                } else {
                    Toast.makeText(this, "照片获取失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case HEAD_IMAGE_CLIP_REQUEST:

                if (popupWindow.isShowing()) {  //检测PopupWindow的状态
                    popupWindow.dismiss();   //关闭PopupWindow
                }

                if (data == null) {
                    return;
                } else {
                    //保存裁剪图片
                    saveCropAvator(data);
                }
                break;

            default:

                if (popupWindow.isShowing()) {  //检测PopupWindow的状态
                    popupWindow.dismiss();   //关闭PopupWindow
                }
                break;
        }
    }

    /**---------------------------------------------
     * 进行照片裁剪
     *
     * @param uri
     *-----------------------------------------------*/
    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    /**---------------------------------------------
     * 保存裁剪的头像
     *
     * @param data
     *-----------------------------------------------*/
    private void saveCropAvator(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Log.i("life", "avatar - bitmap = " + bitmap);
            if (bitmap != null) {
                bitmap = toRoundCorner(bitmap, 10);//调用圆角处理方法

                //上传图片
                uploadHeadImage(bitmap);
                //释放内存
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    /**-------------------------------------------------------------------------
     * 将图片变为圆角
     *
     * @param bitmap
     *            原Bitmap图片
     * @param pixels
     *            图片圆角的弧度(单位:像素(px))
     * @return 带有圆角的图片(Bitmap 类型)
     *----------------------------------------------------------------------------*/
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private void uploadHeadImage(final Bitmap bitmap){
        //图片文件
        final File headImageFile = saveBitmapFile(bitmap);
         /*------------------------------------------
         *    寻找当前用户的头像
         *    查询到：动态加载到ImageView
         *    （下面是调用bmob的示例代码）
         *-----------------------------------------*/
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("userID", myID);//发送者ID
        query.setLimit(1);//返回1条数据，如果不加上这条语句，默认返回10条数据
        query.findObjects(new FindListener<UserInfo>() {//执行查询方法
            final Bitmap fbitmap = bitmap;
            @Override
            public void done(final List<UserInfo> object, BmobException e) {
                if (e == null) {

                    //上传头像
                    bmobFile = new BmobFile(headImageFile);
                    bmobFile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){ //头像上传成功

                                if(object.size() <= 0){//没有该用户信息，用户信息新建并且上传

                                    userInfo = new UserInfo(myID);
                                    userInfo.setHead(bmobFile);
                                    userInfo.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String objectId, BmobException e) {
                                            if(e==null){
                                                Log.i("bmob","创建数据成功：" + objectId);
                                                //设置头像
                                                headImage.setImageBitmap(fbitmap);
                                                ToastUtil.toast(getApplicationContext(), "头像上传成功");
//                                                //更新本地缓存数据
//                                                GGIMHelper.getInstance().updateCache(myID);
                                            }else{//用户信息上传失败
                                                Log.i("bmob","用户信息上传失败：" + e.getMessage() + "," + e.getErrorCode());
                                                String errMsg = "头像上传失败:" + e.getMessage() + "," + e.getErrorCode();
                                                ToastUtil.toast(getApplicationContext(), errMsg);
                                            }
                                        }
                                    });

                                }else{//有该用户，进行头像更新

                                    String bmobUserOnlyId = object.get(0).getObjectId();

                                    userInfo = new UserInfo(myID);
                                    userInfo.setHead(bmobFile);
                                    userInfo.update(bmobUserOnlyId, new UpdateListener() {

                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                                Log.i("bmob","更新成功");
                                                //设置头像
                                                headImage.setImageBitmap(fbitmap);
                                                ToastUtil.toast(getApplicationContext(), "头像上传成功");
                                            }else{
                                                Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                                String errMsg = "头像更新失败:" + e.getMessage() + "," + e.getErrorCode();
                                                ToastUtil.toast(getApplicationContext(), errMsg);
                                            }
                                        }
                                    });

                                }

                            }else//头像上传失败
                            {
                                Log.i("bmob","头像上传失败："+e.getMessage()+","+e.getErrorCode());
                                String errMsg = "头像上传失败:" + e.getMessage() + "," + e.getErrorCode();
                                ToastUtil.toast(getApplicationContext(), errMsg);
                            }
                        }
                    });

                } else {//查询失败，头像不上传
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                    String errMsg = "网络故障：" + e.getMessage() + "," + e.getErrorCode();
                    ToastUtil.toast(getApplicationContext(), errMsg);
                }
            }
        });
    }

    /**-------------------------------------------------------------------------
     * Bitmap转为File
     *
     * @param bitmap
     *            原Bitmap图片
     * @return File 转换后文件
     *----------------------------------------------------------------------------*/
    public File saveBitmapFile(Bitmap bitmap){
        String filepath = PathUtil.getApplicationPath(UserInfoActivity.this)+"/head_temp.jpg";
        Log.i(TAG, filepath);
        File file=new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
