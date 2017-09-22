package tatai.htk;

/**
 * This interface is to define the methods that any controller needs to implement in order for the HTK class
 * to be able to update the GUI at certain stages of its recording task.
 */
public interface HTKListener {
	public void recordingComplete();
}
