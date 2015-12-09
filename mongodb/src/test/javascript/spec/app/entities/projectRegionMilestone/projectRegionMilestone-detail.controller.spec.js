'use strict';

describe('ProjectRegionMilestone Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProjectRegionMilestone;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProjectRegionMilestone = jasmine.createSpy('MockProjectRegionMilestone');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProjectRegionMilestone': MockProjectRegionMilestone
        };
        createController = function() {
            $injector.get('$controller')("ProjectRegionMilestoneDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:projectRegionMilestoneUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
