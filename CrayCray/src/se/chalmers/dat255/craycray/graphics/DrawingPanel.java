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
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class DrawingPanel extends SurfaceView implements Callback {
	private PanelThread thread;
	private Bitmap cray;
	private float x = 0;
	private float y = 0;
	
	public DrawingPanel(Context context) {
		super(context);
		
		this.getHolder().addCallback(this);
		this.thread = new PanelThread(getHolder());
		this.setFocusable(true);
		this.setClickable(true);
		
		setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch (View view, MotionEvent e){
				Log.w("CrayCray", "onTouch");
			switch(e.getAction()){
			case MotionEvent.ACTION_MOVE:
				x = e.getX();
				y = e.getY();
				break;
			}
				return true;
			}
		});
	}
	public DrawingPanel(Context context, AttributeSet attr){
		super(context, attr);
	}
	public DrawingPanel(Context context, AttributeSet attr, int defStyle){
		super(context, attr, defStyle);
	}

	public void startDrawPanel() {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		try {
			thread.join();
			retry = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(b);
		cray = BitmapFactory.decodeResource(getResources(), R.drawable.happy_child);
		canvas.drawColor(color.white);
		canvas.drawBitmap(cray, x, y, null);

		thread.setRunning(false);
		Bitmap bug = BitmapFactory.decodeResource(getResources(), R.drawable.blue_bug);
		//canvas.drawBitmap(bug, 0, 0, null);

	}

	private class PanelThread extends Thread {
		private SurfaceHolder surfaceHolder;
		private boolean run = false;

		public PanelThread(SurfaceHolder holder) {
			this.surfaceHolder = holder;
		}

		public void setRunning(boolean running) {
			this.run = running;
		}

		@Override
		public void run() {
			Canvas c;
			while (run) {
				c = null;
				try {
					c = this.surfaceHolder.lockCanvas(null);
					synchronized (this.surfaceHolder) {

						DrawingPanel.this.onDraw(c);
					}
				} finally {
					if (c != null) {
						surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

}
