package com.optimize.android;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Arrays;
import java.util.List;

public abstract class EasyAdapter<T> extends BaseAdapter {

	protected Context context;
	protected List<T> items;
	protected int layoutResId;

	public EasyAdapter(T[] items, int layoutResId) {
		this(null, items, layoutResId);
	}
	public EasyAdapter(Context context, T[] items, int layoutResId) {
		this(context, Arrays.asList(items), layoutResId);
	}
	public EasyAdapter(List<T> items, int layoutResId) {
		this(null, items, layoutResId);
	}
	public EasyAdapter(Context context, List<T> items, int layoutResId) {
		this.context = context;
		this.items = items;
		this.layoutResId = layoutResId;
	}

	public EasyAdapter<T> bindTo(ListActivity list) {
		this.context = list;
		list.setListAdapter(this);
		return this;
	}

	@SuppressWarnings("unchecked")
	public EasyAdapter<T> bindTo(AdapterView list, Context context) {
		this.context = context;
		list.setAdapter(this);
		return this;
	}

	@SuppressWarnings("unchecked")
	public EasyAdapter<T> bindTo(AdapterView list, android.app.Fragment fragment) {
		return bindTo(list, fragment.getActivity());
	}

	@SuppressWarnings("unchecked")
	public EasyAdapter<T> bindTo(AdapterView list, android.support.v4.app.Fragment fragment) {
		return bindTo(list, fragment.getActivity());
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = this.getLayoutInflater();
			view = inflater.inflate(this.layoutResId, parent, false);
		}
		T item = (T) this.getItem(position);
		ViewDelegate viewDelegate = new ViewDelegate(view);
		bindView(viewDelegate, item, position);
		return view;
	}

	protected LayoutInflater getLayoutInflater() {
		if (context == null) throw new NullPointerException("No context given");
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	protected abstract void bindView(ViewDelegate view, T item, int position);

	public static class ViewDelegate {
		protected View view;
		protected ViewDelegate(View view) {
			this.view = view;
		}
		public <TView> TView view(int viewId, Class<TView> viewClass) {
			return (TView) view.findViewById(viewId);
		}
		public View getUnderlyingView() {
			return this.view;
		}
		public void setGone(int viewId, boolean gone) {
			view(viewId, View.class).setVisibility(gone ? View.GONE : View.VISIBLE);
		}
		public void setInvisible(int viewId, boolean invisible) {
			view(viewId, View.class).setVisibility(invisible ? View.INVISIBLE : View.VISIBLE);
		}
		public void setImageUrl(int viewId, String url) {
			setImageUrl(viewId, url, 0);
		}
		public void setImageUrl(int viewId, String url, int loadingImageResource) {
			ImageView iv = view(viewId, ImageView.class);
			if (loadingImageResource == 0) iv.setImageDrawable(null);
			else iv.setImageResource(loadingImageResource);
			ImageLoader.getInstance().displayImage(url, iv);
		}
		public void setImageBitmap(int viewId, Bitmap bitmap) {
			ImageView iv = view(viewId, ImageView.class);
			if(bitmap != null) {
				iv.setImageBitmap(bitmap);
			}
		}
		public void setImageResource(int viewId, int imgResId) {
			view(viewId, ImageView.class).setImageResource(imgResId);
		}
		public void setText(int viewId, String text) {
			view(viewId, TextView.class).setText(text);
		}
		public void setBackgroundColor(int viewId, int color) { view(viewId, View.class).setBackgroundColor(color); }
		public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
			view(viewId, View.class).setOnClickListener(onClickListener);
		}
	}

}
