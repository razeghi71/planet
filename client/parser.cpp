#include "parser.h"
#include "planet.h"
#include <iostream>

using namespace std;

Parser::Parser(World *_w)
{
    w=_w;

}

void Parser::Parse(string &msg)
{
    int pindex = msg.find_last_of("$");
    int aindex = msg.find_last_of("#");
    int pid=-1  ,aid=-1;
    Planet tp;
    Agent ta;
    string pMsg = msg.substr(pindex+1,aindex-pindex-1);
    string aMsg = msg.substr(aindex+1);
    stringstream ss(pMsg);
    string ts;
    int tmp,tmp2;
    while(!ss.eof()){
        ss.ignore(2);
        ss>>ts;
        ss>>pid;
        if(pid!=-1){
            tp.setID(pid);
            ss.ignore(3);

            ss>>ts;
            ss>>tmp;
            tp.setDiameter(tmp);
            ss.ignore(3);

            ss>>ts;
            ss>>ts;
            tp.SetOwner(ts);
            ss.ignore(3);

            ss>>ts;
            ss>>tmp;
            tp.setNoA(tmp);
            ss.ignore(3);


            ss>>ts;
            ss>>tmp;
            ss.ignore(3);
            ss>>ts;
            ss>>tmp2;

            tp.setPosition(Point(tmp,tmp2));
            ss.ignore(2);

            ss.ignore(1);
            w->updatePlanet(pid,tp);
        }
        pid =-1;
    }
    ss.clear();
    ss.str(aMsg);
    long iter = 0;
    while(!ss.eof()){
        ss.ignore(2);
        ss>>ts;
        ss>>aid;
        if(aid!=-1){
            ss.ignore(3);

            ss>>ts;
            ss>>ts;
            ta.SetTeam(ts);
            ss.ignore(3);


            ss>>ts;
            ss>>tmp;
            ss.ignore(3);
            ss>>ts;
            ss>>tmp2;
            ta.setPos(Point(tmp,tmp2));
            ss.ignore(3);

            ss>>ts;
            ss>>tmp;
            ta.setDest(tmp);
            ss.ignore(3);

            ss>>ts;
            ss>>tmp;
            ta.setStrenght(tmp);
            ss.ignore(2);

            ss.ignore(1);
            for(iter ; iter<aid ; iter++)
                w->deleteAgent(iter);
            w->addAgent(aid,ta);
            iter++;
        }
        aid=-1;
    }
}
