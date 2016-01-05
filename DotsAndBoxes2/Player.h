//
//  Player.h
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Player : NSObject
-(id)initWithName:(NSString*)name colorChoice:(NSUInteger)color;
-(NSNumber*)r;
-(NSNumber*)g;
-(NSNumber*)b;
-(NSString*) getName;
-(void) addPoint;
-(NSUInteger) getScore;
@end
