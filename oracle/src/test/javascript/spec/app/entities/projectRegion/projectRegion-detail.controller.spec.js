'use strict';

describe('ProjectRegion Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProjectRegion, MockProject, MockRegion, MockStatus;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProjectRegion = jasmine.createSpy('MockProjectRegion');
        MockProject = jasmine.createSpy('MockProject');
        MockRegion = jasmine.createSpy('MockRegion');
        MockStatus = jasmine.createSpy('MockStatus');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProjectRegion': MockProjectRegion,
            'Project': MockProject,
            'Region': MockRegion,
            'Status': MockStatus
        };
        createController = function() {
            $injector.get('$controller')("ProjectRegionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:projectRegionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
