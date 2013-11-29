#include "decide.h"
#include <vector>
#include <cstdlib>
#include <float.h>
#include <iostream>
using namespace std;
Decide::Decide(World &_w):w(_w)
{
}

string Decide::decide()
{
    return RandomBot();
}

string Decide::RageBot()
{
    vector<Planet> myPlanets = w.getMyPlanets();
    for (vector<Planet>::iterator its=myPlanets.begin(); its!=myPlanets.end(); ++its){
        if (its->getNumAgents() < 10 * its->getGrowthRate()) {
            continue;
        }
        Planet dest ;
        int bestDistance = 999999;
        vector<Planet> enemyPlanets = w.getEnemyPlanets();
        for (vector<Planet>::iterator itd=enemyPlanets.begin(); itd!=enemyPlanets.end(); ++itd){
            int dist = w.getDistance(*its, *itd);
            if (dist < bestDistance) {
                bestDistance = dist;
                dest = *itd;
            }
        }
        return w.sendAgent(*its,dest,its->getNumAgents());

    }
    return "";
}

string Decide::RandomBot()
{
    vector<Planet> my,other;
    // (1) If we current have a fleet in flight, then do nothing until it
    // arrives.
    //    if (w.getMyAgents().size() >= 1) {
    //        return "";
    //    }

    // (2) Pick one of my planets at random.
    // (3) Pick a target planet at random.
    // (4) Send half the ships from source to dest.
    my = w.getMyPlanets();
    other = w.getOtherPlanets();
    if(my.size()>0 && other.size()>0 ){
        Planet source = my[rand() % (my.size())]
                , dest =other[rand() % (other.size())];
        return  w.sendAgent( source , dest , source.getNumAgents()/2 );
    }
    return "";

}

string Decide::ProspectorBot()
{
    // (1) If we current have a fleet in flight, just do nothing.
    //    if (w.getMyAgents().size() >= 1) {
    //        return "";
    //    }
    // (2) Find my strongest planet.
    Planet source;
    double sourceScore =0;
    vector<Planet> myPlanets;
    myPlanets = w.getMyPlanets();
    for (vector<Planet>::iterator it=myPlanets.begin(); it!=myPlanets.end(); ++it){
        double score = (double)it->getNumAgents() / (1 + it->getGrowthRate());
        if (score > sourceScore) {
            sourceScore = score;
            source = *it;
        }
    }
    // (3) Find the weakest enemy or neutral planet.
    Planet dest ;
    double destScore = 0;
    vector<Planet> otherPlanets;
    otherPlanets = w.getOtherPlanets();
    for (vector<Planet>::iterator it=otherPlanets.begin(); it!=otherPlanets.end(); ++it){
        double score = (double)(1 + it->getGrowthRate()) / it->getNumAgents();
        if (score > destScore) {
            destScore = score;
            dest = *it;
        }
    }
    // (4) Send half the ships from my strongest planet to the weakest
    // planet that I do not own.
    return w.sendAgent(source , dest , source.getNumAgents()/2);
}

//string Decide::DualBot()
//{
//    int numAgents = 1;
//    bool attackMode = false;
//    if (w.getNumAgents(0) > w.getNumAgents(1)) {
//        if (w.getProduction(0) > w.getProduction(1)) {
//            numAgents = 1;
//            attackMode = true;
//        } else {
//            numAgents = 3;
//        }
//    }
//    else {
//        if (w.getProduction(0) > w.getProduction(1)) {
//            numAgents = 1;
//        } else {
//            numAgents = 5;
//        }
//    }
//    // (1) If we current have more tha numAgents fleets in flight, just do
//    // nothing until at least one of the fleets arrives.
////    if (pw.MyFleets().size() >= numAgents) {
////        return "";
////    }
//    // (2) Find my strongest planet.
//    Planet source;
//    double sourceScore =0;
//    vector<Planet> myPlanets;
//    myPlanets = w.getMyPlanets();
//    for (vector<Planet>::iterator it=myPlanets.begin(); it!=myPlanets.end(); ++it){
//        double score = (double)it->getNumAgents() / (1 + it->getGrowthRate());
//        if (score > sourceScore) {
//            sourceScore = score;
//            source = *it;
//        }
//    }
//    // (3) Find the weakest enemy or neutral planet.
//    Planet dest ;
//    double destScore = 0;
//    vector<Planet> candidates;
//    if(attackMode)
//        candidates = w.getEnemyPlanets();
//    for (vector<Planet>::iterator it=candidates.begin(); it!=candidates.end(); ++it){
//        double score = (double)(1 + it->getGrowthRate()) / it->getNumAgents();
//        if (score > destScore) {
//            destScore = score;
//            dest = *it;
//        }
//    }

//    // (4) Send half the ships from my strongest planet to the weakest
//    // planet that I do not own.
//    return w.sendAgent(source , dest , source.getNumAgents()/2);

//}


