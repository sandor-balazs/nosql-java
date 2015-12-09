'use strict';

describe('ProjectCountryMilestone Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProjectCountryMilestone;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProjectCountryMilestone = jasmine.createSpy('MockProjectCountryMilestone');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProjectCountryMilestone': MockProjectCountryMilestone
        };
        createController = function() {
            $injector.get('$controller')("ProjectCountryMilestoneDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:projectCountryMilestoneUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
