'use strict';

describe('ProjectMilestone Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProjectMilestone;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProjectMilestone = jasmine.createSpy('MockProjectMilestone');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProjectMilestone': MockProjectMilestone
        };
        createController = function() {
            $injector.get('$controller')("ProjectMilestoneDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:projectMilestoneUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
