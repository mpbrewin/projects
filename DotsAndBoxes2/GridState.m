//
//  GameState.m
//  DotsAndBoxes
//
//  Created by student on 12/9/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import "GridState.h"

#define x0 39
#define x1 149
#define x2 262
#define x3 371
#define y0 103
#define y1 213
#define y2 324
#define y3 433

@interface GridState()
@property(strong,nonatomic) NSMutableArray* grid;
@property(readwrite,nonatomic) NSUInteger x;
@property(readwrite,nonatomic) NSUInteger y;
@end
@implementation GridState

-(instancetype) init{
    if (self = [super init]){
        self.grid = [[NSMutableArray alloc] initWithCapacity:3]; //Array will include lines and spaces (3x3)
        for(int i = 0; i < 3; i++){
            self.grid[i] = [[NSMutableArray alloc] initWithCapacity:3];
            for(int j = 0; j < 3; j++){
                self.grid[i][j] = @(0);
            }
        }
    }
    return self;
}

-(void) addLine:(NSUInteger)xs xEnd:(NSUInteger)xe yStart:(NSUInteger)ys yEnd:(NSUInteger)ye{
    //first col
    if(xs == x0 && ys == y0){
        self.grid[0][0] = @([self.grid[0][0] intValue]+1);
    }
    else if(xs == x0 && ys == y1){
        if(xe == x1){
            self.grid[0][0] = @([self.grid[0][0] intValue]+1);
            self.grid[0][1] = @([self.grid[0][1] intValue]+1);
        }
        else if(ye == y0){
           self.grid[0][0] = @([self.grid[0][0] intValue]+1);
        }
        else{
          self.grid[0][1] = @([self.grid[0][1] intValue]+1);
        }
    }
    else if(xs == x0 && ys == y2){
        if(xe == x1){
            self.grid[0][1] = @([self.grid[0][1] intValue]+1);
            self.grid[0][2] = @([self.grid[0][2] intValue]+1);
        }
        else if(ye == y1){
            self.grid[0][1] = @([self.grid[0][1] intValue]+1);
        }
        else{
            self.grid[0][2] = @([self.grid[0][2] intValue]+1);
        }
    }
    else if(xs == x0 && ys == y3){
        self.grid[0][2] = @([self.grid[0][2] intValue]+1);
    }
    
    //second col
    else if(xs == x1 && ys == y0){
        if(xe == x2){
            self.grid[1][0] = @([self.grid[1][0] intValue]+1);
        }
        else if(xe == x0){
            self.grid[0][0] = @([self.grid[0][0] intValue]+1);
        }
        else{
            self.grid[0][0] = @([self.grid[0][0] intValue]+1);
            self.grid[1][0] = @([self.grid[1][0] intValue]+1);
        }
    }
    else if(xs == x1 && ys == y1){
        if(xe == x2){
            self.grid[1][0] = @([self.grid[1][0] intValue]+1);
            self.grid[1][1] = @([self.grid[1][1] intValue]+1);
        }
        else if(ye == y0){
            self.grid[1][0] = @([self.grid[1][0] intValue]+1);
            self.grid[0][0] = @([self.grid[0][0] intValue]+1);
        }
        else if(xe == x0){
            self.grid[0][0] = @([self.grid[0][0] intValue]+1);
            self.grid[0][1] = @([self.grid[0][1] intValue]+1);
            
        }else{
            self.grid[1][1] = @([self.grid[1][1] intValue]+1);
            self.grid[0][1] = @([self.grid[0][1] intValue]+1);
        }
    }
    else if(xs == x1 && ys == y2){
        if(xe == x2){
            self.grid[1][1] = @([self.grid[1][1] intValue]+1);
            self.grid[1][2] = @([self.grid[1][2] intValue]+1);
        }
        else if(ye == y1){
           self.grid[0][1] = @([self.grid[0][1] intValue]+1);
            self.grid[1][1] = @([self.grid[1][1] intValue]+1);
        }
        else if(xe == x0){
            self.grid[0][1] = @([self.grid[0][1] intValue]+1);
            self.grid[0][2] = @([self.grid[0][2] intValue]+1);
        }
        else{
            self.grid[0][2] = @([self.grid[0][2] intValue]+1);
            self.grid[1][2] = @([self.grid[1][2] intValue]+1);
        }
    }
    else if(xs == x1 && ys == y3){
        if(xe == x2){
            self.grid[1][2] = @([self.grid[1][2] intValue]+1);
        }
        else if(ye == y2){
            self.grid[1][2] = @([self.grid[1][2] intValue]+1);
            self.grid[0][2] = @([self.grid[0][2] intValue]+1);
        }
        else{
            self.grid[0][2] = @([self.grid[0][2] intValue]+1);
        }
    }
    
    
    else if(xs == x2 && ys == y0){
        if(xe == x3){
            self.grid[2][0] = @([self.grid[2][0] intValue]+1);
        }
        else if(xe == x1){
            self.grid[1][0] = @([self.grid[1][0] intValue]+1);
        }
        else{
            self.grid[1][0] = @([self.grid[1][0] intValue]+1);
            self.grid[2][0] = @([self.grid[2][0] intValue]+1);
        }
    }
    else if(xs == x2 && ys == y1){
        if(xe == x3){
            self.grid[2][0] = @([self.grid[2][0] intValue]+1);
            self.grid[2][1] = @([self.grid[2][1] intValue]+1);
        }
        else if(ye == y0){
            self.grid[2][0] = @([self.grid[2][0] intValue]+1);
            self.grid[1][0] = @([self.grid[1][0] intValue]+1);
        }
        else if(xe == x1){
            self.grid[1][0] = @([self.grid[1][0] intValue]+1);
            self.grid[1][1] = @([self.grid[1][1] intValue]+1);
            
        }else{
            self.grid[1][1] = @([self.grid[1][1] intValue]+1);
            self.grid[2][1] = @([self.grid[2][1] intValue]+1);
        }
    }
    else if(xs == x2 && ys == y2){
        if(xe == x3){
            self.grid[2][1] = @([self.grid[2][1] intValue]+1);
            self.grid[2][2] = @([self.grid[2][2] intValue]+1);
        }
        else if(ye == y1){
            self.grid[2][1] = @([self.grid[2][1] intValue]+1);
            self.grid[1][1] = @([self.grid[1][1] intValue]+1);
        }
        else if(xe == x1){
            self.grid[1][1] = @([self.grid[1][1] intValue]+1);
            self.grid[1][2] = @([self.grid[1][2] intValue]+1);
        }
        else{
            self.grid[1][2] = @([self.grid[1][2] intValue]+1);
            self.grid[2][2] = @([self.grid[2][2] intValue]+1);
        }
    }
    else if(xs == x2 && ys == y3){
        if(xe == x3){
            self.grid[2][2] = @([self.grid[2][2] intValue]+1);
        }
        else if(ye == y2){
            self.grid[2][2] = @([self.grid[2][2] intValue]+1);
            self.grid[1][2] = @([self.grid[1][2] intValue]+1);
        }
        else{
            self.grid[1][2] = @([self.grid[1][2] intValue]+1);
        }
    }
    
    
    
    else if(xs == x3 && ys == y0){
        self.grid[2][0] = @([self.grid[2][0] intValue]+1);
    }
    else if(xs == x3 && ys == y1){
        if(xe == x2){
            self.grid[2][0] = @([self.grid[2][0] intValue]+1);
            self.grid[2][1] = @([self.grid[2][1] intValue]+1);
        }
        else if(ye == y0){
            self.grid[2][0] = @([self.grid[2][0] intValue]+1);
        }
        else{
            self.grid[2][1] = @([self.grid[2][1] intValue]+1);
        }
    }
    else if(xs == x3 && ys == y2){
        if(xe == x2){
            self.grid[2][1] = @([self.grid[2][1] intValue]+1);
            self.grid[2][2] = @([self.grid[2][2] intValue]+1);
        }
        else if(ye == y1){//check above
            self.grid[2][1] = @([self.grid[2][1] intValue]+1);
        }
        else{
            self.grid[2][2] = @([self.grid[2][2] intValue]+1);
        }
    }
    else if(xs == x3 && ys == y3){///
        self.grid[2][2] = @([self.grid[2][2] intValue]+1);
    }
}

-(NSMutableArray*) getUpdatedGrid{
    return self.grid;
}

@end
