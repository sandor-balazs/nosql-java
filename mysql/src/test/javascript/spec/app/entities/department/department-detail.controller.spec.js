'use strict';

describe('Department Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDepartment, MockLocation;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDepartment = jasmine.createSpy('MockDepartment');
        MockLocation = jasmine.createSpy('MockLocation');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Department': MockDepartment,
            'Location': MockLocation
        };
        createController = function() {
            $injector.get('$controller')("DepartmentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:departmentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
