//
//  GameGridView.m
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import "GameGridView.h"
#import "Line.h"
#define x0 35
#define x1 145
#define x2 258
#define x3 365
#define y0 130
#define y1 240
#define y2 353
#define y3 460
/*#define y0 70
#define y1 180
#define y2 293
#define y3 400
 */

@interface GameGridView ()
@property(strong,nonatomic) NSMutableArray* lines;

//p1 color
@property(readwrite,nonatomic) CGFloat r1;
@property(readwrite,nonatomic) CGFloat g1;
@property(readwrite,nonatomic) CGFloat b1;
//p2 color
@property(readwrite,nonatomic) CGFloat r2;
@property(readwrite,nonatomic) CGFloat g2;
@property(readwrite,nonatomic) CGFloat b2;

@property(readwrite,nonatomic) NSInteger turn;

@end

@implementation GameGridView

- (id)initWithCoder:(NSCoder *)aCoder{
    if(self = [super initWithCoder:aCoder]){
        self.lines = [[NSMutableArray alloc] init];
        self.turn = 0;
    }
    return self;
}

// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    CGContextRef context = UIGraphicsGetCurrentContext();
    //Draw grid coordinates
    CGContextFillEllipseInRect(context, CGRectMake(x0, y0, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x1, y0, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x2, y0, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x3, y0, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x0, y1, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x1, y1, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x2, y1, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x3, y1, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x0, y2, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x1, y2, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x2, y2, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x3, y2, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x0, y3, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x1, y3, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x2, y3, 7, 7));
    CGContextFillEllipseInRect(context, CGRectMake(x3, y3, 7, 7));
    
    CGContextSetLineWidth(context, 2.0);
    //Draw any existing lines
    //Separate it from the line drawn in GameLineView, or else we would be redrawing this
    //Every time touchmoved is called
    if(self.lines != nil){
        for(NSInteger i = 0; i < self.lines.count; i++){
            if(self.turn == 0)
                CGContextSetRGBStrokeColor(context, self.r1, self.g1, self.b1, 1.0);
            else
                CGContextSetRGBStrokeColor(context, self.r2, self.g2, self.b2, 1.0);
            CGContextMoveToPoint(context, [[self.lines objectAtIndex:i] xStart], [[self.lines objectAtIndex:i] yStart]);
            CGContextAddLineToPoint(context, [[self.lines objectAtIndex:i] xEnd], [[self.lines objectAtIndex:i] yEnd]);
            CGContextStrokePath(context);
            self.turn = (self.turn+1)%2;
        }
    }
    
}

-(void) setColor:(NSNumber*)r green:(NSNumber*)g blue:(NSNumber*)b red2:(NSNumber*)r2 green2:(NSNumber*)g2 blue2:(NSNumber*)b2{
    self.r1 = [r floatValue];
    self.g1 = [g floatValue];
    self.b1 = [b floatValue];
    
    self.r2 = [r2 floatValue];
    self.g2 = [g2 floatValue];
    self.b2 = [b2 floatValue];
}

- (void) addLine: (Line*) l{
    self.turn = 0;
    [self setNeedsDisplay];
    [self.lines addObject:l];
}


@end
