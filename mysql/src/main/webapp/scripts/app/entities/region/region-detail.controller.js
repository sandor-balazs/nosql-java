'use strict';

angular.module('ontimeApp')
    .controller('RegionDetailController', function ($scope, $rootScope, $stateParams, entity, Region, Country) {
        $scope.region = entity;
        $scope.load = function (id) {
            Region.get({id: id}, function(result) {
                $scope.region = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:regionUpdate', function(event, result) {
            $scope.region = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
