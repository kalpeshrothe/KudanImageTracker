package eu.kudan.kudansamples;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARArbiTrack;
import eu.kudan.kudan.ARGyroPlaceManager;
import eu.kudan.kudan.ARImageNode;

/**
 * Created by Kalpesh.Rothe on 10/23/2017.
 */

public class TestingActivity extends ARActivity implements GestureDetector.OnGestureListener
{

    ARGyroPlaceManager gyroPlaceManager;
    ARArbiTrack arbiTrack;
    private GestureDetectorCompat gestureDetect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        gestureDetect = new GestureDetectorCompat(this,this);




    }
    private void addImageTrackable() {

        // Create a node to be tracked.
        ARImageNode trackingNode = new ARImageNode("Cow Tracking.png");

        // Rotate the node to ensure it is displayed correctly.
        trackingNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f);
        trackingNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f);

        // Add the node as a child of the ArbiTracker's world.
        arbiTrack.getWorld().addChild(trackingNode);

    }

    private void addImageNode() {
        // Create a node to be used as the target.
        ARImageNode targetNode = new ARImageNode("Cow Target.png");
        // Add it to the Gyro Placement Manager's world so that it moves with the device's Gyroscope.
        gyroPlaceManager.getWorld().addChild(targetNode);
        // Rotate and scale the node to ensure it is displayed correctly.
        targetNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f);
        targetNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f);
        targetNode.scaleByUniform(0.3f);
        // Set the ArbiTracker's target node.
        arbiTrack.setTargetNode(targetNode);

    }
    @Override
    public void setup() {
        super.setup();

        // Initialise ArbiTrack.
        arbiTrack = ARArbiTrack.getInstance();
        arbiTrack.initialise();

        // Initialise gyro placement.
        gyroPlaceManager = ARGyroPlaceManager.getInstance();
        gyroPlaceManager.initialise();
        addImageTrackable();
        addImageNode();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        ARArbiTrack arbiTrack = ARArbiTrack.getInstance();

        // If arbitrack is tracking, stop tracking so that its world is no longer rendered, and make the target node visible.
        if (arbiTrack.getIsTracking())
        {

            arbiTrack.stop();
            arbiTrack.getTargetNode().setVisible(true);
        }

        // If it's not tracking, start tracking and hide the target node.
        else
        {

            arbiTrack.start();
            arbiTrack.getTargetNode().setVisible(false);
        }

        return false;
    }

    // We also need to implement the other overrides of the GestureDetector, though we don't need them for this sample.
    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        return false;
    }

}
