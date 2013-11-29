#include "connection.h"
#include "clientsocket.h"
Connection::Connection(std::string host, int port)
{
    client_socket = new ClientSocket( host, port );
}

void Connection::Send(std::string msg)
{
    (*client_socket) << msg << "\n" ;
}

//void Connection::Send(const char *msg)
//{
////    (*client_socket) << msg << "\n";

//}

std::string Connection::Recieve()
{
    std::string msg;
    (*client_socket) >> msg;
    return msg;
}
