#include "planet.h"



void Planet::setDiameter(int _diameter)
{
    diameter=_diameter;
}

int Planet::getDiameter()
{
    return diameter;
}

int Planet::getGrowthRate()
{
    return diameter/30;
}

void Planet::SetOwner(string _owner)
{
    owner=_owner;
}

string Planet::getOwner()
{
    return owner;
}

void Planet::setNoA(int _num)
{
    numberOfAgents = _num;
}

int Planet::getNumAgents()
{
    return numberOfAgents;
}

void Planet::setPosition(Point _pos)
{
    Position = _pos;
}

Point Planet::getPosition()
{
    return Position;
}

void Planet::setID(int _ID)
{
    ID=_ID;
}

int Planet::getID()
{
    return ID;
}

void Planet::operator =(Planet _planet)
{
    setDiameter(_planet.diameter);
    this->setID(_planet.ID);
    this->setNoA(_planet.numberOfAgents);
    this->SetOwner(_planet.owner);
    this->setPosition(_planet.Position);
}

