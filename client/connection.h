#ifndef CONNECTION_H
#define CONNECTION_H

#include "clientsocket.h"

class Connection
{
public:
    Connection(std::string host,int port);
    void Send(std::string msg);
//    void Send(const char *msg);
    std::string Recieve();
private:
    ClientSocket *client_socket;
};

#endif // CONNECTION_H
