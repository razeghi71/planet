#include "point.h"

Point::Point(int _x, int _y)
{
    x=_x;
    y=_y;
}

Point::Point()
{
}

void Point::setX(int _x)
{
    x = _x;
}

int Point::getX()
{
    return x;
}

void Point::setY(int _y)
{
    y = _y;
}

int Point::getY()
{
    return y;
}

