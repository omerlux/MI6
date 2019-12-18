package bgu.spl.mics;

import java.util.concurrent.TimeUnit;
import java.util.Timer;

/**
 * A Future object represents a promised result - an object that will
 * eventually be resolved to hold a result of some operation. The class allows
 * Retrieving the result once it is available.
 * 
 * Only private methods may be added to this class.
 * No public constructor is allowed except for the empty constructor.
 */
public class Future<T> {
	//------------start edit--------------------**/
	private  T result;
	private boolean isDone;
	//------------end edit----------------------**/

	/**
	 * This should be the the only public constructor in this class.
	 */
	public Future() {
		//TODO: implement this
		//------------start edit--------------------**/
		result = null;
		isDone = false;
		//------------end edit ---------------------**/
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved.
     * This is a blocking method! It waits for the computation in case it has
     * not been completed.
     * <p>
     * @return return the result of type T if it is available, if not wait until it is available.
     * 	       
     */
	public T get() throws InterruptedException {
		//TODO: implement this.
		//------------start edit - 17/12----------------------**/
		synchronized (this){
			if (!isDone)		//changed from while to if
				this.wait();
			//this.notifyAll();
			return this.result;
		}
		//TODO: add wait until complete
		//------------end edit - 17/12-----------------------**/
	}
	
	/**
     * Resolves the result of this Future object.
     */
	public void resolve (T result) {
		//TODO: implement this.
		//------------start edit - 16/12--------------------**/
		synchronized (this){
			if(!isDone){
				this.result=result;
				isDone=true;
				this.notifyAll();				//Wake up the threads that waits for answer
			}
		}
		//------------end edit - 16/12----------------------**/
	}
	
	/**
     * @return true if this object has been resolved, false otherwise
     */
	public boolean isDone() {
		//------------start edit - 16/12--------------------/
		synchronized (this) {
			return isDone;
		}
		//------------end edit - 16/12----------------------/
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved,
     * This method is non-blocking, it has a limited amount of time determined
     * by {@code timeout}
     * <p>
     * @param timeout 	the maximal amount of time units to wait for the result.
     * @param unit		the {@link TimeUnit} time units to wait.
     * @return return the result of type T if it is available, if not, 
     * 	       wait for {@code timeout} TimeUnits {@code unit}. If time has
     *         elapsed, return null.
     */
	public T get(long timeout, TimeUnit unit) throws InterruptedException {
		//------------start edit - 16/12--------------------/
		synchronized (this){
			if(!isDone){
				this.wait(unit.toNanos(timeout));	//waits untill passed time, or notified
			}	//only 1 time will be resolved, than it surely made isDone=true
			return result;
		}
		//TODO: implement this.
		//TODO: using timer
/*		synchronized (this){
			while (!isDone & timeout>0); //busy wait
				//timeout t = unit.wait(timeout);
			this.notifyAll();
			return result;                          //Result initialized as null, so if resolve() won't be activate it will return null
			}
*/
		}
		//------------end edit - 16/12----------------------/
	}
