'use strict';

describe('ProjectCountry Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProjectCountry, MockProjectRegion, MockCountry, MockStatus;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProjectCountry = jasmine.createSpy('MockProjectCountry');
        MockProjectRegion = jasmine.createSpy('MockProjectRegion');
        MockCountry = jasmine.createSpy('MockCountry');
        MockStatus = jasmine.createSpy('MockStatus');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProjectCountry': MockProjectCountry,
            'ProjectRegion': MockProjectRegion,
            'Country': MockCountry,
            'Status': MockStatus
        };
        createController = function() {
            $injector.get('$controller')("ProjectCountryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:projectCountryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
