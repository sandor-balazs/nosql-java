'use strict';

describe('Country Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCountry, MockLocation, MockRegion;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCountry = jasmine.createSpy('MockCountry');
        MockLocation = jasmine.createSpy('MockLocation');
        MockRegion = jasmine.createSpy('MockRegion');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Country': MockCountry,
            'Location': MockLocation,
            'Region': MockRegion
        };
        createController = function() {
            $injector.get('$controller')("CountryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ontimeApp:countryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
