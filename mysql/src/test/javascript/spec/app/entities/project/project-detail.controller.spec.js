'use strict';

describe('Project Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProject, MockStatus, MockFunding;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProject = jasmine.createSpy('MockProject');
        MockStatus = jasmine.createSpy('MockStatus');
        MockFunding = jasmine.createSpy('MockFunding');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Project': MockProject,
            'Status': MockStatus,
            'Funding': MockFunding
        };
        createController = function() {
            $injector.get('$controller')("ProjectDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:projectUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
