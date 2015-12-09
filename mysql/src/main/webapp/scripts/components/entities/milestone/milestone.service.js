'use strict';

angular.module('ontimeApp')
    .factory('Milestone', function ($resource, DateUtils) {
        return $resource('api/milestones/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
