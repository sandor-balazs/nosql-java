'use strict';

angular.module('ontimeApp')
    .controller('CountryDetailController', function ($scope, $rootScope, $stateParams, entity, Country) {
        $scope.country = entity;
        $scope.load = function (id) {
            Country.get({id: id}, function(result) {
                $scope.country = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:countryUpdate', function(event, result) {
            $scope.country = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
