#ifndef WORLD_H
#define WORLD_H

#include <map>
#include <string>
#include <vector>
using namespace std;

class Planet;
class Agent;


typedef map<int , Planet> planetMap;
typedef map<int , Agent> agentMap;
typedef pair<int , Planet> planetPair;
typedef pair<int , Agent> agentPair;

using namespace std;

class World
{
public:
    World();
    void setTeamName(string _teamName);
    string getTeamName();
    void setWidth(int _width);
    void setHeight(int _height);
    int getWidth();
    int getHeight();
    void updatePlanet(int _id ,Planet _planet);
    void addAgent(int _id ,Agent _agent);
    void deleteAgent( int _id);

    // Returns the production of the given player.
    int getProduction(int i);
    // Returns the distance between two planets, rounded up to the next highest integer
    int getDistance(Planet source , Planet dest);

    // Returns the number of planets.
    int getNumPlanets();
    // Returns the number of Agents.
    int getNumAgents(int i);
    // Returns a list of all the planets.
    vector<Planet> getPlanets();
    // Returns a list of all the black holes.
    vector<Planet> getBlackHoles();
    // Return a list of all the planets owned by the current player.
    vector<Planet> getMyPlanets();
    // Return a list of all the planets owned by rival players.
    vector<Planet> getEnemyPlanets();
    // Return a list of all neutral planets.
    vector<Planet> getNeutralPlanets();
    // Return a list of all the planets that are not owned by the current player
    vector<Planet> getOtherPlanets();
    // Return a list of all the agents owned by the current player.
    vector<Agent> getMyAgents();
    //
    std::string sendAgent(Planet _from , Planet _to , int nr);

private:
    string teamName;
    planetMap planets;
    agentMap agents;
    int width ,height;

};

#endif // WORLD_H
