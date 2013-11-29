// Definition of the Socket class

#ifndef Socket_class
#define Socket_class


#include <string>

#if defined(_WIN32) && ! defined(__CYGWIN__)
  /*For windows systems:*/
#include <winsock.h> /* for SOCKET and others */
#include <winsock2.h>
   static void sleep(int secs) {Sleep(1000*secs);}
#pragma comment (lib, "wsock32.lib")
#else
    #include <unistd.h>
    #include <netdb.h>
    #include <sys/types.h>
    #include <sys/socket.h>
    #include <netinet/in.h>
    #include <arpa/inet.h>
    #include <sys/wait.h>
#endif

const int MAXHOSTNAME = 200;
const int MAXCONNECTIONS = 5;
const int MAXRECV = 202400;

class Socket
{
public:
    Socket();
    virtual ~Socket();

    // Server initialization
    bool create();
    bool bind ( const int port );
    bool listen() const;
    bool accept ( Socket& ) const;

    // Client initialization
    bool connect ( const std::string host, const int port );

    // Data Transimission
    bool send ( const std::string ) const;
    int recv ( std::string& ) const;

#ifndef WIN32
    void set_non_blocking ( const bool );
#endif
    bool is_valid() const { return m_sock != -1; }

private:
    struct in_addr* getHostByName(const char* server);
    int m_sock;
    sockaddr_in m_addr;
#ifdef WIN32
    WSADATA wsa_data;
#endif

};


#endif
