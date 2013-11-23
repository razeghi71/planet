package PlanetWars;

/**
 *
 * @author mohammad
 */
public class Team {
    private String name ;

    /**
     * Team Class Constructor ... 
     * @param name set team name
     */
    public Team(String name) {
        setName(name);
    }
    
    /**
     * get team name
     * @return teamname
     */
    public String getName() {
        return name;
    }

    /**
     * set team name
     * @param name get team name
     */
    public void setName(String name) {
        this.name = name;
    }

}
