'use strict';

describe('Employment Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployment, MockEmployee, MockDepartment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployment = jasmine.createSpy('MockEmployment');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockDepartment = jasmine.createSpy('MockDepartment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Employment': MockEmployment,
            'Employee': MockEmployee,
            'Department': MockDepartment
        };
        createController = function() {
            $injector.get('$controller')("EmploymentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:employmentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
