'use strict';

describe('Allocation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAllocation, MockProjectCountry, MockEmployment, MockSkill;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAllocation = jasmine.createSpy('MockAllocation');
        MockProjectCountry = jasmine.createSpy('MockProjectCountry');
        MockEmployment = jasmine.createSpy('MockEmployment');
        MockSkill = jasmine.createSpy('MockSkill');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Allocation': MockAllocation,
            'ProjectCountry': MockProjectCountry,
            'Employment': MockEmployment,
            'Skill': MockSkill
        };
        createController = function() {
            $injector.get('$controller')("AllocationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:allocationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
