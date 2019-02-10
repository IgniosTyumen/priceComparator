package Repositories;

import ObjectsProject.Location;

import java.util.HashMap;
import java.util.Map;

public class LocationsOnUpdateRepo {


        static Map<Integer, Location> reposSet;
        private static Repositories.LocationsOnUpdateRepo ourInstance = new Repositories.LocationsOnUpdateRepo();

        private LocationsOnUpdateRepo() {
            reposSet = new HashMap<>();
        }

        public static Repositories.LocationsOnUpdateRepo getInstance() {
            return ourInstance;
        }

        public void putLocationWhereArticleInLocationIsKnown(Location location) {

            Location newLocation = new Location();
            newLocation = ArticleReposOld.getInstance().recallSFC(location.getArticle());
            if (newLocation.getFamily()!=null) {
                reposSet.put(location.getArticle(), location);
            }
        }

        public Map<Integer, Location> getRepoAcess() {
            return reposSet;
        }
    }


