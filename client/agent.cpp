#include "agent.h"

void Agent::SetTeam(string _team)
{
    team = _team;
}

string Agent::getTeam()
{
    return team;
}

void Agent::setPos(Point _pos)
{
    position = _pos;
}

Point Agent::getPosition()
{
    return position;
}

void Agent::setDest(int _dest)
{
    destID = _dest;
}

int Agent::getDestID()
{
    return destID;
}

void Agent::setStrenght(int _strenght)
{
    strenght = _strenght;
}

int Agent::getStrenght()
{
    return strenght;
}


