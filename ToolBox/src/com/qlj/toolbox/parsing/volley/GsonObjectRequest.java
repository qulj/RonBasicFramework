package com.qlj.toolbox.parsing.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Gson 请求处理类
 * 
 * @author qlj
 * @time 2014年9月12日上午11:07:12
 * @param <T>
 */
public class GsonObjectRequest<T> extends Request<T> {

	private final Gson gson = new Gson();
	private final Class<T> clazz;
	private final Map<String, String> headers;
	private final Map<String, String> mParams;
	private final Listener<T> listener;

	public GsonObjectRequest(String url, Class<T> clazz, Map<String, String> headers, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.mParams = params;
		this.listener = listener;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return mParams != null ? mParams : super.getParams();
	}

	@Override
	protected void deliverResponse(T response) {
		// TODO Auto-generated method stub
		listener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}

	}

}
