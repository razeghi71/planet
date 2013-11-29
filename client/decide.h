#ifndef DECIDE_H
#define DECIDE_H

#include "world.h"
#include "agent.h"
#include "planet.h"

class Decide
{
public:
    Decide(World &_w);
    std::string decide();
    std::string RageBot();
    std::string RandomBot();
    std::string ProspectorBot();
//    std::string DualBot();

private:
    World &w;
};

#endif // DECIDE_H
