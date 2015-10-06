import com.vaadin.server.Resource;
import it.algos.webbase.web.lib.LibResource;
import it.algos.webbase.web.lib.LibResourceOld;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by gac on 23 lug 2015.
 * Resource
 */
public class LibResTest {

    private static String IMMAGINE = "action_add.png";

    @Test
    public void getImgBytes() {
        byte[] bytes = null;
        String prePath = "";

        bytes = LibResource.getImgBytes(prePath, IMMAGINE);
        assertNull(bytes);

        prePath = "/Users/gac/Documents/IdeaProjects/evento/out/artifacts/evento/WEB-INF/data/img/";
        bytes = LibResource.getImgBytes(prePath, IMMAGINE);
        assertNotNull(bytes);

        prePath = "/Users/gac/Documents/IdeaProjects/webbase/out/production/webbase/it/algos/web/data/img/";
        bytes = LibResource.getImgBytes(prePath, IMMAGINE);
        assertNotNull(bytes);
    }// end of single test

    @Test
    public void getImgResource() {
        Resource res = null;
        String prePath = "";

        res = LibResource.getImgResource(prePath, IMMAGINE);
        assertNull(res);

        prePath = "/Users/gac/Documents/IdeaProjects/evento/out/artifacts/evento/WEB-INF/data/img/";
        res = LibResource.getImgResource(prePath, IMMAGINE);
        assertNotNull(res);

        prePath = "/Users/gac/Documents/IdeaProjects/webbase/out/production/webbase/it/algos/web/data/img/";
        res = LibResource.getImgResource(prePath, IMMAGINE);
        assertNotNull(res);
    }// end of single test

}// end of test class