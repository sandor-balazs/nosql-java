'use strict';

angular.module('ontimeApp')
    .controller('ProjectRegionMilestoneDetailController', function ($scope, $rootScope, $stateParams, entity, ProjectRegionMilestone) {
        $scope.projectRegionMilestone = entity;
        $scope.load = function (id) {
            ProjectRegionMilestone.get({id: id}, function(result) {
                $scope.projectRegionMilestone = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:projectRegionMilestoneUpdate', function(event, result) {
            $scope.projectRegionMilestone = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
