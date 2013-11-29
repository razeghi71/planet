#ifndef PARSER_H
#define PARSER_H

#include "agent.h"
#include "world.h"
#include <string>
#include <sstream>

class Parser
{
public:
    Parser(World *w);
    void Parse(std::string &msg);
private:
    World *w;
};

#endif // PARSER_H
