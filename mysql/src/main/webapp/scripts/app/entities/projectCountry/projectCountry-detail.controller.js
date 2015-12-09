'use strict';

angular.module('ontimeApp')
    .controller('ProjectCountryDetailController', function ($scope, $rootScope, $stateParams, entity, ProjectCountry, ProjectRegion, Country, Status) {
        $scope.projectCountry = entity;
        $scope.load = function (id) {
            ProjectCountry.get({id: id}, function(result) {
                $scope.projectCountry = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:projectCountryUpdate', function(event, result) {
            $scope.projectCountry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
