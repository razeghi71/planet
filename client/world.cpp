#include "world.h"
#include "planet.h"
#include "agent.h"
#include <iostream>
#include <sstream>
#include <cmath>

using namespace std;

World::World()
{
}

void World::setTeamName(string _teamName)
{
    teamName = _teamName;
}

string World::getTeamName()
{
    return teamName;
}

void World::setWidth(int _width)
{
    width=_width;
}

void World::setHeight(int _height)
{
    height=_height;
}

int World::getWidth()
{
    return width;
}

int World::getHeight()
{
    return height;
}

void World::updatePlanet(int _id, Planet _planet)
{
    planets[_id] = _planet;
}

void World::addAgent(int _id, Agent _agent)
{
    agents[_id] = _agent;
}

void World::deleteAgent(int _id)
{
    agents.erase(_id);
}

int World::getProduction(int i)
{
    int prod = 0;
    if(i==0)
        for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it){
            if(it->second.getOwner() == teamName){
                prod += it->second.getGrowthRate();
            }
        }
    else
        for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it)
            if(it->second.getOwner() != teamName && it->second.getOwner() != "none" && it->second.getOwner() != "Blackhole"){
                prod += it->second.getGrowthRate();
            }

    return prod;
}

int World::getDistance(Planet source, Planet dest)
{
    Point s=source.getPosition();
    Point d=dest.getPosition();
    return ceil(sqrt( (s.getX()-d.getX())^2 + (s.getY()-d.getY())^2 ));
}


int World::getNumPlanets()
{
    return planets.size();
}

int World::getNumAgents(int i)
{
    int num=0;
    if(i==0){
        for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it)
            if(it->second.getOwner() == teamName)
                num += it->second.getNumAgents();
        for (agentMap::iterator it=agents.begin(); it!=agents.end(); ++it)
            if(it->second.getTeam() == teamName)
                num += it->second.getStrenght();
    }
    else{
        for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it)
            if(it->second.getOwner() != teamName && it->second.getOwner() != "none" && it->second.getOwner() != "Blackhole")
                num += it->second.getNumAgents();
        for (agentMap::iterator it=agents.begin(); it!=agents.end(); ++it)
            if(it->second.getTeam() != teamName)
                num += it->second.getStrenght();
    }
    return num;
}

vector<Planet> World::getPlanets()
{
    vector<Planet> p;
    for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it)
        if(it->second.getOwner() != "Blackhole")
            p.push_back(it->second);
    return p;
}

vector<Planet> World::getBlackHoles()
{
    vector<Planet> p;
    for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it)
        if(it->second.getOwner() == "Blackhole")
            p.push_back(it->second);
    return p;

}

vector<Planet> World::getMyPlanets()
{

    vector<Planet> my;
    for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it){
        if(it->second.getOwner() == teamName)
            my.push_back(it->second);
    }
    return my;

}

vector<Planet> World::getEnemyPlanets()
{

    vector<Planet> enemy;
    for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it){
        if(it->second.getOwner() != teamName && it->second.getOwner() != "none" && it->second.getOwner() != "Blackhole")
            enemy.push_back(it->second);
    }
    return enemy;

}

vector<Planet> World::getNeutralPlanets()
{

    vector<Planet> neutral;
    for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it){
        if(it->second.getOwner() == "none")
            neutral.push_back(it->second);
    }
    return neutral;

}

vector<Planet> World::getOtherPlanets()
{
    vector<Planet> other;
    for (planetMap::iterator it=planets.begin(); it!=planets.end(); ++it)
        if(it->second.getOwner() != teamName && it->second.getOwner() != "Blackhole")
            other.push_back(it->second);
    return other;
}

vector<Agent> World::getMyAgents()
{
    vector<Agent> myAgents;
    for (agentMap::iterator it=agents.begin(); it!=agents.end(); ++it){
        if(it->second.getTeam() == teamName )
            myAgents.push_back(it->second);
    }
    return myAgents;
}

std::string World::sendAgent(Planet _from, Planet _to, int nr)
{
    stringstream ss;
    ss << _from.getID() << " " << _to.getID() << " " << nr ;
    return ss.str();
}
