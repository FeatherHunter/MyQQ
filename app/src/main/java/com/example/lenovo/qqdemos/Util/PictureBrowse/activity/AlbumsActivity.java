package com.example.lenovo.qqdemos.Util.PictureBrowse.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Util.PictureBrowse.adapter.AlbumsAdapter;
import com.example.lenovo.qqdemos.Util.PictureBrowse.bean.PhotoUpImageBucket;
import com.example.lenovo.qqdemos.Util.PictureBrowse.utils.PhotoUpAlbumHelper;

public class AlbumsActivity extends Activity {

	private GridView gridView;
	private AlbumsAdapter adapter;
	private PhotoUpAlbumHelper photoUpAlbumHelper;
	private List<PhotoUpImageBucket> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.albums_gridview);
		init();
		loadData();
		onItemClick();
	}
	private void init(){
		gridView = (GridView) findViewById(R.id.album_gridv);
		adapter = new AlbumsAdapter(AlbumsActivity.this);
		gridView.setAdapter(adapter);
	}
	
	private void loadData(){
		photoUpAlbumHelper = PhotoUpAlbumHelper.getHelper();
		photoUpAlbumHelper.init(AlbumsActivity.this);
		photoUpAlbumHelper.setGetAlbumList(new PhotoUpAlbumHelper.GetAlbumList() {
			@Override
			public void getAlbumList(List<PhotoUpImageBucket> list) {
				adapter.setArrayList(list);
				adapter.notifyDataSetChanged();
				AlbumsActivity.this.list = list;
			}
		});
		photoUpAlbumHelper.execute(false);
	}
	
	private void onItemClick(){
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AlbumsActivity.this,AlbumItemActivity.class);
				intent.putExtra("imagelist", list.get(position));
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
