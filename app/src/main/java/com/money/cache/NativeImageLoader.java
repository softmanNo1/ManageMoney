package com.money.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;


public class NativeImageLoader {
	/**
	 * 缓存强引用如果本身的size不够大，继续往里面放入值会把前面老的缓存都清除
	 */
    private LruCache<String, Bitmap> memoryCache;
    /**
     * 单例
     */
    private static NativeImageLoader nativeImageLoader = new NativeImageLoader();
    /**
     * 任务现成
     */
    private ExecutorService excutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
    
    private NativeImageLoader(){
    	/**
    	 * 可用内存
    	 */
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    	/**
    	 * 缓存最大值
    	 */
    	int maxSize = maxMemory / 4;
    	
    	memoryCache = new LruCache<String, Bitmap>(maxSize){
    		
    		@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
			}
    		
    	};
    }
    /**
     * 获取单例对象
     * @return
     */
    public  static NativeImageLoader getInstance(){
    	return nativeImageLoader;
    }
    
    /**
     * 获取本地图片
     * @param path
     * @param mCallBack
     * @return
     */
    public Bitmap loadNativeImage(final String path, final NativeImageCallBack mCallBack){
		return this.loadNativeImage(path, null, mCallBack);
	}
    
    /**
     * 获取本地图片
     * @param path
     * @param point
     * @param mCallBack
     * @return
     */
    public Bitmap loadNativeImage(final String path,final Point point, final NativeImageCallBack mCallBack){
  		 Bitmap bitmap = getBitmapFromMemCache(path);
  		 
  		 final Handler handler = new Handler(){
              @Override
            public void handleMessage(Message msg) {
            	super.handleMessage(msg);
            	mCallBack.onImageLoader((Bitmap)msg.obj, path);
            }  			 
  		 };
  		 
  		 if(bitmap == null){
  			  excutorService.execute(new Runnable() {
				@Override
				public void run() {
					Bitmap mBitmap = decodeThumbBitmapForFile(path, point == null ? 0: point.x, point == null ? 0: point.y);
					Message msg = handler.obtainMessage();
					msg.obj = mBitmap;
					handler.sendMessage(msg);
					addBitmapToMemoryCache(path, mBitmap);
				}
			});
  		 }
  		 return bitmap;
  	}
    
    
    /**
	 * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
	 * @param path
	 * @param viewWidth
	 * @param viewHeight
	 * @return
	 */
	private Bitmap decodeThumbBitmapForFile(String path, int viewWidth, int viewHeight){
		BitmapFactory.Options options = new BitmapFactory.Options();
		//设置为true,表示解析Bitmap对象，该对象不占内存
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		//设置缩放比例
		options.inSampleSize = computeScale(options, viewWidth, viewHeight);
		
		//设置为false,解析Bitmap对象加入到内存中
		options.inJustDecodeBounds = false;
		
		return BitmapFactory.decodeFile(path, options);
	}
	
	/**
	 * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
	 * @param options
	 * @param viewWidth
	 * @param viewHeight
	 */
	private int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight){
		int inSampleSize = 1;
		if(viewWidth == 0 || viewWidth == 0){
			return inSampleSize;
		}
		int bitmapWidth = options.outWidth;
		int bitmapHeight = options.outHeight;
		
		//假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
		if(bitmapWidth > viewWidth || bitmapHeight > viewWidth){
			int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
			int heightScale = Math.round((float) bitmapHeight / (float) viewWidth);
			
			//为了保证图片不缩放变形，我们取宽高比例最小的那个
			inSampleSize = widthScale < heightScale ? widthScale : heightScale;
		}
		return inSampleSize;
	}
    
    /**
     * 获取缓存数据
     * @param key
     * @return
     */
    private Bitmap getBitmapFromMemCache(String key){
    	return memoryCache.get(key);
    }
    /**
     * 向缓存中添加数据
     * @param key
     * @param bitmap
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null && bitmap != null) {
			memoryCache.put(key, bitmap);
		}
	}
    
    /**
     * 回调接口
     * @author GSF
     *
     */
    public interface NativeImageCallBack{
    	public void onImageLoader(Bitmap bitmap, String path);
    }
}
