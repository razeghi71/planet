#ifndef PLANET_H
#define PLANET_H

#include "point.h"
#include <string>
using namespace std;

class Planet
{
public:
    void setDiameter(int _diameter);
    int getDiameter();
    int getGrowthRate();
    void SetOwner(string _owner);
    string getOwner();
    void setNoA(int _num);
    int getNumAgents();
    void setPosition(Point _pos);
    Point getPosition();
    void setID(int _ID);
    int getID();
    void operator = (const Planet planet);
private:
    int ID;
    int diameter;
    string owner;
    int numberOfAgents;
    Point Position;
};

#endif // PLANET_H
