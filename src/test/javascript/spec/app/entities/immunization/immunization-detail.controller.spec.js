'use strict';

describe('Controller Tests', function() {

    describe('Immunization Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockImmunization, MockNonPathologicBkg;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockImmunization = jasmine.createSpy('MockImmunization');
            MockNonPathologicBkg = jasmine.createSpy('MockNonPathologicBkg');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Immunization': MockImmunization,
                'NonPathologicBkg': MockNonPathologicBkg
            };
            createController = function() {
                $injector.get('$controller')("ImmunizationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:immunizationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
