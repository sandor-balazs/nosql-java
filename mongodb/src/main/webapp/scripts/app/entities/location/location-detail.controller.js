'use strict';

angular.module('ontimeApp')
    .controller('LocationDetailController', function ($scope, $rootScope, $stateParams, entity, Location) {
        $scope.location = entity;
        $scope.load = function (id) {
            Location.get({id: id}, function(result) {
                $scope.location = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:locationUpdate', function(event, result) {
            $scope.location = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
