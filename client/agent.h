#ifndef AGENT_H
#define AGENT_H
#include "point.h"
#include "planet.h"
using namespace std;
class Agent
{
public:
    void SetTeam(string _team);
    string getTeam();
    void setPos(Point _pos);
    Point getPosition();
    void setDest(int _dest);
    //void setDest(Point _dest);
    int getDestID();
    void setStrenght(int _strenght);
    int getStrenght();
private:
    string team;
    Point position;
    int destID;
    int strenght;
};

#endif // AGENT_H
