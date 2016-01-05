//
//  Player.m
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import "Player.h"
@interface Player()
@property(strong,nonatomic) NSString* name;
@property(strong,nonatomic) NSMutableArray* color;
@property(readwrite,nonatomic) NSUInteger score;

@end
@implementation Player

-(id)initWithName:(NSString*)name colorChoice:(NSUInteger)color{
    if(self = [super init]){
        self.name = name;
        if(color == 0){
            self.color = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithFloat:1.0],[NSNumber numberWithFloat:0.0],[NSNumber numberWithFloat:0.0], nil];
        }
        else if(color == 1){
            self.color = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithFloat:1.0],[NSNumber numberWithFloat:0.5],[NSNumber numberWithFloat:0.0], nil];
        }
        else if(color == 2){
            self.color = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithFloat:1.0],[NSNumber numberWithFloat:1.0],[NSNumber numberWithFloat:0.0], nil];
        }
        else if(color == 3){
            self.color = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithFloat:0.3],[NSNumber numberWithFloat:0.8],[NSNumber numberWithFloat:0.1], nil];
        }
        else if(color == 4){
            self.color = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithFloat:0.0],[NSNumber numberWithFloat:0.0],[NSNumber numberWithFloat:1.0], nil];
        }
        else if(color == 5){
            self.color = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithFloat:0.0],[NSNumber numberWithFloat:1.0],[NSNumber numberWithFloat:1.0], nil];
        }
        else if(color == 6){
            self.color = [[NSMutableArray alloc] initWithObjects:[NSNumber numberWithFloat:0.8],[NSNumber numberWithFloat:0.4],[NSNumber numberWithFloat:1.0], nil];
        }
        
        self.score = 0;
    
    }
    return self;
}

-(NSNumber*)r{
    return self.color[0];
}
-(NSNumber*)g{
    return self.color[1];
}
-(NSNumber*)b{
    return self.color[2];
}

-(NSString*) getName{
    return self.name;
}

-(void) addPoint{
    self.score++;
}

-(NSUInteger) getScore{
    return self.score;
}

@end
