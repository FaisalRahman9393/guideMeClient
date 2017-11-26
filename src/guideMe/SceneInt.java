/**
 * Created by Faisal Rahman on 19/01/2017.
 */

package guideMe;
    public interface SceneInt {
    /**As scenes are going to be changed through controllers this is...
     * An interface for changing scenes through controllers
     * @param s - the name the scene given by a controller
     */
        public void setScreenParent(GuideMeSceneContainer s);
    }