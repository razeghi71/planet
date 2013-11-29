#ifndef POINT_H
#define POINT_H

class Point
{
public:
    Point(int _x,int _y);
    Point();
    void setX(int _x);
    int getX();
    void setY(int _y);
    int getY();
private:
    int x;
    int y;
};

#endif // POINT_H
