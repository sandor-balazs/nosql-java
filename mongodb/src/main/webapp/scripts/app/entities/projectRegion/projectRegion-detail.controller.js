'use strict';

angular.module('ontimeApp')
    .controller('ProjectRegionDetailController', function ($scope, $rootScope, $stateParams, entity, ProjectRegion) {
        $scope.projectRegion = entity;
        $scope.load = function (id) {
            ProjectRegion.get({id: id}, function(result) {
                $scope.projectRegion = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:projectRegionUpdate', function(event, result) {
            $scope.projectRegion = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
