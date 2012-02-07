/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.physics.engine;

/**
 *
 * @author mauro
 */
public class Atomic3Float {

	final Object syncObj = new Object();
	float a[] = new float[3];
	float rit[] = new float[3];
	
	void set(float x, float y, float z) {
		synchronized(syncObj){
			this.a[0] = x;
			this.a[1] = y;
			this.a[2] = z;
		}
	}

	public float[] get() {
		synchronized(syncObj){
			System.arraycopy(a, 0, rit, 0, 3);
		}
		return rit;
	}
	
}
