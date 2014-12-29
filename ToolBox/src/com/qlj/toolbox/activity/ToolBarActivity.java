package com.qlj.toolbox.activity;

import com.qlj.toolbox.R;
import com.qlj.toolbox.util.FontUtil;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ToolBarActivity extends ActionBarActivity
{

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle actionBarDrawerToggle;

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_bar_layout);

		initToobarandDrawer();
		initView();
	}

	private void initView()
	{
		TextView tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setTypeface(FontUtil.getRegularTypeface(this));

		findViewById(R.id.btn_close).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (drawerLayout.isDrawerVisible(GravityCompat.START))
				{
					drawerLayout.closeDrawer(GravityCompat.START);// 关闭抽屉
				} else
				{
					drawerLayout.openDrawer(GravityCompat.START);// 打开抽屉
				}
			}
		});
	}

	private void initToobarandDrawer()
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
		toolbar.inflateMenu(R.menu.tool_bar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("抽屉效果");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				toolbar, R.string.drawer_open, R.string.drawer_close);
		actionBarDrawerToggle.syncState();
		drawerLayout.setDrawerListener(actionBarDrawerToggle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tool_bar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
