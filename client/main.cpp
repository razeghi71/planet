#include "clientsocket.h"
#include "SocketException.h"
#include <iostream>
#include <string>
#include <cstdlib>
#include <sstream>
#include <vector>
#include "parser.h"
#include "world.h"
#include "parser.h"
#include "connection.h"
#include "decide.h"

//using namespace std;
int main ( int argc, char **argv )
{
    std::string teamName(argv[1]);
    World *w=new World;
    Parser p(w);
    Decide dec(*w);
    w->setTeamName(teamName);
    stringstream  command;
    command.str(w->getTeamName());
    try
    {
        std::string host ((argc>2)?argv[2]:"localhost");
        int port = ((argc>3)?atoi(argv[3]):4000);
        Connection connection( host, port );
        std::string reply;
        try
        {
            std::string msg =command.str();
            connection.Send(msg);
            while(1){
                command.str("");
                reply = connection.Recieve();
                //cout<<reply<<endl;
                if(reply.at(0) == '%')
                    break;
                p.Parse(reply);
                command<<dec.decide();
                connection.Send((std::string)(command.str()));

            }
        }
        catch ( SocketException& ) {}


    }
    catch ( SocketException& e )
    {
        std::cout << "Exception was caught:" << e.description() << "\n";
    }

    return 0;
}
