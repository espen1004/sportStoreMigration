﻿using Microsoft.AspNetCore.Mvc;
using SportsStore.Models;
using System.Linq;
using Microsoft.AspNetCore.Authorization;

namespace SportsStore.Controllers {

    [Authorize]
    public class AdminController : Controller {
        /*
        public AdminController() {
        }

        public ViewResult Index() {
           return RedirectToAction("Index");
        }    

        public ViewResult Edit(int productId) {
            //View(repository.Products.FirstOrDefault(p => p.ProductID == productId));
            return RedirectToAction("Index");
            }
        
        [HttpPost]
        public IActionResult Edit(Product product) {
         //   if (ModelState.IsValid) {
          //      repository.SaveProduct(product);
           //     TempData["message"] = $"{product.Name} has been saved";
            //    return RedirectToAction("Index");
          //  } else {
                // there is something wrong with the data values
            //    return View(product);
           // }
           return RedirectToAction("Index");
        }

        public ViewResult Create() => View("Edit", new Product());

        [HttpPost]
        public IActionResult Delete(int productId) {
          //  Product deletedProduct = repository.DeleteProduct(productId);
           // if (deletedProduct != null) {
           //     TempData["message"] = $"{deletedProduct.Name} was deleted";
           // }
           // return RedirectToAction("Index");
        }

        [HttpPost]
        public IActionResult SeedDatabase() {
            return RedirectToAction(nameof(Index));
        }
        */
    }
}
