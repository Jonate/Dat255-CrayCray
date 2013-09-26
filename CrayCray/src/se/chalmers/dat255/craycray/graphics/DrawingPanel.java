package se.chalmers.dat255.craycray.graphics;

import se.chalmers.dat255.craycray.R;
import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class DrawingPanel extends SurfaceView implements Callback{
	private PanelThread thread;
	

	public DrawingPanel(Context context) {
		super(context);
		
	}
	public DrawingPanel(Context context, AttributeSet attrs){
		super(context, attrs);
		this.getHolder().addCallback(this);
		this.thread = new PanelThread(getHolder());
		this.setFocusable(true);
	}
	public DrawingPanel(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	
	public void startDrawPanel(){
		thread.setRunning(true);
		thread.start();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		try{
			thread.join();
			retry = false;
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onDraw(Canvas canvas){
		Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
		Canvas can = new Canvas(b);
		Bitmap cray = BitmapFactory.decodeResource(getResources(), R.drawable.happy_child);
		Bitmap bug = BitmapFactory.decodeResource(getResources(), R.drawable.blue_bug);

		canvas.drawColor(color.white);
			canvas.drawBitmap(bug, getMatrix(), null);

			canvas.drawBitmap(cray, 300, 700, null);

		thread.setRunning(false);
		
	}
	

	private class PanelThread extends Thread{
		private SurfaceHolder surfaceHolder;
		private boolean run = false;
		
		public PanelThread (SurfaceHolder holder){
			this.surfaceHolder = holder;			
		}
		public void setRunning (boolean running){
			this.run = running;
		}
		@Override
		public void run(){
			Canvas c;
			while (run){
				c = null;
				
				try{
					
					c = this.surfaceHolder.lockCanvas(null);
					synchronized (this.surfaceHolder){
						
						DrawingPanel.this.onDraw(c);
					}
				}finally{
					if (c != null){
						surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}
}
