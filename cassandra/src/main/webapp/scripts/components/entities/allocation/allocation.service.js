'use strict';

angular.module('ontimeApp')
    .factory('Allocation', function ($resource, DateUtils) {
        return $resource('api/allocations/:id', {}, {
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
